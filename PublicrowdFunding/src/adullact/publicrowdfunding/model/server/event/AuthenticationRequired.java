package adullact.publicrowdfunding.model.server.event;

public interface AuthenticationRequired {
	void errorAuthenticationRequired();
    void errorForbidden();
}
