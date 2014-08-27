package adullact.publicrowdfunding.model.local.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import adullact.publicrowdfunding.model.local.ressource.Project;

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
}
