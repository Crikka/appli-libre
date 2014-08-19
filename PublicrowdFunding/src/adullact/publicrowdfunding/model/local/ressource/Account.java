package adullact.publicrowdfunding.model.local.ressource;

import android.content.Context;
import android.content.SharedPreferences;

import org.joda.time.DateTime;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import adullact.publicrowdfunding.PublicrowdFundingApplication;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import adullact.publicrowdfunding.model.server.entities.RowAffected;
import adullact.publicrowdfunding.model.server.entities.ServerAccount;
import adullact.publicrowdfunding.model.server.entities.Service;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import adullact.publicrowdfunding.model.server.request.CreateRequest;
import rx.Observable;

/**
 * Created by Ferrand on 16/07/2014.
 */
public class Account extends Resource<Account, ServerAccount, ServerAccount> {
    /* ---- Singleton ---- */
    private static Account m_own = null;
    private void initialize() throws NoAccountExistsInLocal {
        SharedPreferences sharedPreferences = m_context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if(!sharedPreferences.contains(KEY_USERNAME) || !sharedPreferences.contains(KEY_USERNAME) || !sharedPreferences.contains(KEY_USERNAME)) {
        	{
        		m_own = null;
        		throw new NoAccountExistsInLocal();
        	}
        }

        m_username = sharedPreferences.getString(KEY_USERNAME, "");
        m_password = sharedPreferences.getString(KEY_PASSWORD, "");
        m_lastSync = Utility.stringToDateTime(sharedPreferences.getString(KEY_LAST_SYNC, ""));
    }

    public static Account getOwn() throws NoAccountExistsInLocal {
        if(m_own == null) {
            Account account =  new Account(PublicrowdFundingApplication.context());
            m_own = account;
            m_own.initialize();
        }

        return m_own;
    }

    public static Account getOwnOrAnonymous() {
        if(m_own == null) {
            return (new Account());
        }

        return m_own;
    }
    private Account(Context context) {
        this.m_context = context;
    }
    /* ------------------- */

    /* --- Static const to store --- */
    private static final String FILE_NAME = "logs";
    private static final String KEY_USERNAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_LAST_SYNC = "last sync";
    /* ----------------------------- */

    /* ----- Resource ----- */
    @Override
    public String getResourceId() {
        return m_username;
    }

    @Override
    public void setResourceId(String id) {
        this.m_username = id;
    }

    @Override
    public ServerAccount toServerResource() {
        ServerAccount res = new ServerAccount();
        res.username = m_username;
        res.password = m_password;
        res.administrator = m_administrator;
        res.pseudo = m_user.getResourceId();
        return res;
    }

    @Override
    public Account makeCopyFromServer(ServerAccount serverAccount) {
        return null;
    }

    @Override
    public Account syncFromServer(ServerAccount serverAccount) {
    	 this.m_username = serverAccount.username;
         this.m_lastSync = DateTime.now();
         this.m_administrator = serverAccount.administrator;
         this.m_anonymous = false;
         this.m_context = PublicrowdFundingApplication.context();
         this.m_user = new User().getCache(serverAccount.pseudo);

         return this;
    }

    @Override
    public Observable<ServerAccount> methodGET(Service service) {
        return service.detailAccount(getResourceId());
    }

    @Override
    public Observable<SimpleServerResponse> methodPUT(Service service) {
        return service.modifyAccount(toServerResource(), getResourceId());
    }

    @Override
    public Observable<RowAffected> methodPOST(Service service) {
        return service.createAccount(toServerResource());
    }

    @Override
    public Observable<SimpleServerResponse> methodDELETE(Service service) {
        return service.deleteAccount(getResourceId());
    }

    @Override
    public Observable<ArrayList<ServerAccount>> methodGETAll(Service service, Map<String,String> filter) {
        return service.listAccount(filter);
    }
    /* -------------------- */

    /* ---- Own data ---- */
    private String m_username;
    private String m_password;
    private DateTime m_lastSync;
    private boolean m_administrator;
    private boolean m_anonymous;
    private Context m_context;
   /* ------------------ */

    /* --- References --- */
    private Cache<User> m_user;
    /* ------------------ */

    public Account() {
        this.m_username = null;
        this.m_password = null;
        this.m_lastSync = null;
        this.m_administrator = false;
        this.m_anonymous = true;
        this.m_context = null;
    }

    public Account(String username, String password, String pseudo) {
        this.m_username = username;
        this.m_password = password;
        this.m_user = new User().getCache(pseudo);
        this.m_lastSync = null;
        this.m_administrator = false;
        this.m_anonymous = false;
        this.m_context = null;
    }

    public void setOwn() {
        m_own = this;
    }

    public void setLastSync(DateTime lastSync) {
        this.m_lastSync = lastSync;
    }

    public boolean isAdmin() {
        return m_administrator;
    }

    public DateTime getLastSync() {
        return m_lastSync;
    }

    public String getUsername() {
        return m_username;
    }

    public String getPassword() {
        return m_password;
    }

    public String getPseudo() {
        return m_user.getResourceId();
    }

    public void getUser(WhatToDo<User> userWhatToDo) {
        m_user.toResource(userWhatToDo);
    }

    private void save() {
        SharedPreferences sharedPreferences = m_context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        sharedPreferences.edit().putString(KEY_USERNAME, m_username);
        sharedPreferences.edit().putString(KEY_PASSWORD, m_password);
        sharedPreferences.edit().putString(KEY_LAST_SYNC, Utility.DateTimeToString(m_lastSync));
        sharedPreferences.edit().commit();
    }


    /* --------- Cryptography part ------------ */
    private final static String HEX = "0123456789ABCDEF";
    private static String encrypt(String seed, String password) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, password.getBytes());
        return toHex(result);
    }

    private static String decrypt(String seed, String encrypted) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        keyGenerator.init(128, sr); // 192 and 256 bits may not be available
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] raw = secretKey.getEncoded();
        return raw;
    }


    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    private static String toHex(String txt) {
        return toHex(txt.getBytes());
    }
    private static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }

    private static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }
    /* ---------------------------------------- */
}
