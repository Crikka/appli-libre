package adullact.publicrowdfunding.controller.addProject;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout;
import com.touchmenotapps.carousel.simple.HorizontalCarouselStyle;
import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout.CarouselInterface;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.custom.CarouselAdaptor;
import adullact.publicrowdfunding.custom.ProjectAdaptor;
import adullact.publicrowdfunding.fragment.v4.profile.ProfilePagerFragment;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class addLocationProjectFragment extends Fragment implements
		OnMapClickListener {

	private GoogleMap map;
	private Marker marker;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_maps, container, false);

		InitialiseMap();

		/*
		 * Intent data = getIntent(); data.getExtras(); Bundle extras3 =
		 * data.getExtras(); if (extras3 != null) { LatLng position = new
		 * LatLng(extras3.getDouble("latitude"),
		 * extras3.getDouble("longitude")); marker = map.addMarker(new
		 * MarkerOptions().position(position) .title("Votre projet")); } else {
		 * marker = null; }
		 */
		marker = null;
		return view;
	}

	private void InitialiseMap() {
		// TODO Auto-generated method stub
		if (map == null) {
			map = ((SupportMapFragment) getActivity()
					.getSupportFragmentManager()
					.findFragmentById(R.id.map_frag)).getMap();

			if (map != null) {

			}

			map.setOnMapClickListener(this);
			map.setMyLocationEnabled(true);

		}
	}

	@Override
	public void onMapClick(LatLng arg0) {
		Toast.makeText(getActivity().getApplicationContext(),
				"Emplacement de votre projet ajouté", Toast.LENGTH_SHORT)
				.show();
		if (marker == null) {
			marker = map.addMarker(new MarkerOptions().position(arg0).title(
					"Votre projet"));
		} else {
			marker.remove();
			marker = map.addMarker(new MarkerOptions().position(arg0).title(
					"Votre projet"));
		}
	}

	public LatLng getPosition() {
		return marker.getPosition();
	}

}