package adullact.publicrowdfunding.model.local.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import adullact.publicrowdfunding.model.local.ressource.Project;

import com.google.android.gms.maps.model.LatLng;

public class sortProjects {
	
	public static void sortBiggestProjectFirst(ArrayList<Project> projetsToDisplay) {

		// Du plus petit au plus grand
		Collections.sort(projetsToDisplay, new Comparator<Project>() {

			@Override
			public int compare(Project lhs, Project rhs) {
				if (Integer.parseInt(lhs.getRequestedFunding()) >= Integer
						.parseInt(rhs.getRequestedFunding())) {
					return -1;
				} else {
					return 1;
				}
			}
		});

	}

	public static void sortBiggestProjectLast(ArrayList<Project> projetsToDisplay) {

		// Du plus petit au plus grand
		Collections.sort(projetsToDisplay, new Comparator<Project>() {

			@Override
			public int compare(Project lhs, Project rhs) {
				if (Integer.parseInt(lhs.getRequestedFunding()) >= Integer
						.parseInt(rhs.getRequestedFunding())) {
					return 1;
				} else {
					return -1;
				}
			}
		});

	}

	public static void sortAlmostFunded(ArrayList<Project> projetsToDisplay) {

		// Du plus petit au plus grand
		Collections.sort(projetsToDisplay, new Comparator<Project>() {

			@Override
			public int compare(Project lhs, Project rhs) {
				if (lhs.getPercentOfAchievement() >= rhs
						.getPercentOfAchievement()) {
					return -1;
				} else {
					return 1;
				}
			}
		});

	}
	
	public static void sortClothersProject(ArrayList<Project> projetsToDisplay) {

		// Du plus petit au plus grand
		Collections.sort(projetsToDisplay, new Comparator<Project>() {

			@Override
			public int compare(Project lhs, Project rhs) {
				
				LatLng PositionProject1 = lhs.getPosition();
				LatLng PositionProject2 = rhs.getPosition();
				
				int projectOneToMe = Calcul.distance(Share.position, PositionProject1);
				int projectTwoToMe = Calcul.distance(Share.position, PositionProject2);
				
				if (projectOneToMe < projectTwoToMe) {
					return -1;
				} else {
					return 1;
				}
			}
		});
	}
}
