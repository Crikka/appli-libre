package adullact.publicrowdfunding.model.server;

import java.util.Date;

import com.google.android.gms.maps.model.LatLng;

import adullact.publicrowdfunding.Requester;
import adullact.publicrowdfunding.model.event.AuthentificationEvent;
import adullact.publicrowdfunding.model.event.CreateProjectEvent;
import adullact.publicrowdfunding.model.event.CreateUserEvent;
import adullact.publicrowdfunding.model.event.ValidateProjectEvent;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.Project;

public class ExampleAndTest {
	public void authenticationAdmin() {
		Requester.authentificateUser("MisterGate", "azE45WIN", new AuthentificationEvent() {

			@Override
			public void ifUserIsAdministrator() {
				System.out.println(" et je suis admin : " + (user() instanceof Administrator));					
			}

			@Override
			public void onAuthentificate() {
				System.out.println("Je suis "+ user().pseudo()+" "+ user().name()+" "+ user().firstName());					
			}

			@Override
			public void errorUserNotExists(String pseudo, String password) {
				System.out.println("Impossible de trouver " + pseudo + " avec le mot de passe : " + password);			
			}
		});
	}
	
	public void authentificationNormalUser() {
		Requester.authentificateUser("Francis", "123456", new AuthentificationEvent() {

			@Override
			public void ifUserIsAdministrator() {
				System.out.println(" et je suis admin : " + (user() instanceof Administrator));					
			}

			@Override
			public void onAuthentificate() {
				System.out.println("Je suis "+ user().pseudo()+" "+ user().name()+" "+ user().firstName());					
			}

			@Override
			public void errorUserNotExists(String pseudo, String password) {
				System.out.println("Impossible de trouver " + pseudo + " avec le mot de passe : " + password);			
			}
		});
	}
	
	public void authentificationFailUser() {
		Requester.authentificateUser("Miaou", "abjectDominera", new AuthentificationEvent() {

			@Override
			public void ifUserIsAdministrator() {
				System.out.println(" et je suis admin : " + (user() instanceof Administrator));					
			}

			@Override
			public void onAuthentificate() {
				System.out.println("Je suis "+ user().pseudo()+" "+ user().name()+" "+ user().firstName());					
			}

			@Override
			public void errorUserNotExists(String pseudo, String password) {
				System.out.println("Impossible de trouver " + pseudo + " avec le mot de passe : " + password);				
			}
		});
	}

	public void createProject() {
		Requester.createProject("Parking sous terrain","Parking au centre de Montpellier", "50000", new Date(114, 5, 10), new Date(114, 8, 10), new CreateProjectEvent() {
			
			@Override
			public void errorAuthentificationRequired() {
				System.out.println("L'utilisateur n'est pas connecte");
				authentificationNormalUser();
				retry();
			}
			
			@Override
			public void onProjectAdded(Project project) {
				System.out.println("Un projet a été ajouté !");
			}

			@Override
			public void ifUserIsAdministrator() {
				System.out.println("Un admin ! On valide ?");
				Requester.validateProject(project(), new ValidateProjectEvent() {
					
					@Override
					public void errorAdministratorRequired() {}
					
					@Override
					public void onValidateProject(Project project) {
						System.out.println("Et hop on valide!");
					}
				});
			}
		}, new LatLng(50,6));
	}
	
	public void createUser() {
		Requester.createUser("Francis", "123456", "Nicolas", "Du bonchaux", new CreateUserEvent() {
			
			@Override
			public void onCreateUser() {
				System.out.println("youpi");
			}
			
			@Override
			public void errorUsernameAlreadyExists(String username) {
				System.out.println(username+" existe déja");
			}
		});
	}
	/* --------------- */
}
