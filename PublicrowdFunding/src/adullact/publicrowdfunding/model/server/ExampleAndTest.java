package adullact.publicrowdfunding.model.server;

import java.util.ArrayList;
import java.util.Date;

import com.google.android.gms.maps.model.LatLng;

import adullact.publicrowdfunding.model.server.event.AuthenticationEvent;
import adullact.publicrowdfunding.model.server.event.CreateProjectEvent;
import adullact.publicrowdfunding.model.server.event.CreateUserEvent;
import adullact.publicrowdfunding.model.server.event.ModifyAccountEvent;
import adullact.publicrowdfunding.model.server.event.UsersListingEvent;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.User;

public class ExampleAndTest {
	public void authenticationAdmin() {
		UserRequester.authenticateUser("MisterGate", "azE45WIN", new AuthenticationEvent() {

            @Override
            public void ifUserIsAdministrator() {
                System.out.println(" et je suis admin : " + (user() instanceof Administrator));
            }

            @Override
            public void onAuthenticate() {
                System.out.println("Je suis " + user().pseudo() + " " + user().name() + " " + user().firstName());
            }

            @Override
            public void errorUserNotExists(String pseudo, String password) {
                System.out.println("Impossible de trouver " + pseudo + " avec le mot de passe : " + password);
            }
        });
	}

	public void authentificationNormalUser() {
		UserRequester.authenticateUser("Francis", "123456", new AuthenticationEvent() {

            @Override
            public void ifUserIsAdministrator() {
                System.out.println(" et je suis admin : " + (user() instanceof Administrator));
            }

            @Override
            public void onAuthenticate() {
                System.out.println("Je suis " + user().pseudo() + " " + user().name() + " " + user().firstName());
            }

            @Override
            public void errorUserNotExists(String pseudo, String password) {
                System.out.println("Impossible de trouver " + pseudo + " avec le mot de passe : " + password);
            }
        });
	}

	public void authentificationFailUser() {
		UserRequester.authenticateUser("Miaou", "abjectDominera", new AuthenticationEvent() {

            @Override
            public void ifUserIsAdministrator() {
                System.out.println(" et je suis admin : " + (user() instanceof Administrator));
            }

            @Override
            public void onAuthenticate() {
                System.out.println("Je suis " + user().pseudo() + " " + user().name() + " " + user().firstName());
            }

            @Override
            public void errorUserNotExists(String pseudo, String password) {
                System.out.println("Impossible de trouver " + pseudo + " avec le mot de passe : " + password);
            }
        });
	}

	public void createProject() {
		UserRequester.authenticateUser("Francis", "123456", new AuthenticationEvent() {

            @Override
            public void ifUserIsAdministrator() {
                System.out.println(" et je suis admin : " + (user() instanceof Administrator));
            }

            @Override
            public void onAuthenticate() {
                System.out.println("Je suis " + user().pseudo() + " " + user().name() + " " + user().firstName());
                ProjectRequester.createProject("Parking sous terrain", "Parking au centre de Montpellier", "50000", new Date(114, 5, 10), new Date(114, 8, 10), new CreateProjectEvent() {

                    @Override
                    public void errorAuthenticationRequired() {
                        System.out.println("L'utilisateur n'est pas connecte");
                    }

                    @Override
                    public void onProjectAdded(Project project) {
                        System.out.println("Un projet a été ajouté !");
                    }

                    @Override
                    public void ifUserIsAdministrator() {
                        System.out.println("Un admin ! On valide ?");
						/*Requester.validateProject(project(), new ValidateProjectEvent() {

							@Override
							public void errorAdministratorRequired() {}

							@Override
							public void onValidateProject(Project project) {
								System.out.println("Et hop on valide!");
							}
						});*/
                    }
                }, new LatLng(50, 6));
            }

            @Override
            public void errorUserNotExists(String pseudo, String password) {
                System.out.println("Impossible de trouver " + pseudo + " avec le mot de passe : " + password);
            }
        });
	}

	public void createUser() {
		UserRequester.createUser("Francis", "123456", "Nicolas", "Du bonchaux", new CreateUserEvent() {

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

	public void modifyAccount() {
		UserRequester.createUser("Laure25", "150m", "Manodou", "Laura", new CreateUserEvent() {

			@Override
			public void onCreateUser() {
				UserRequester.authenticateUser("Laure25", "150m", new AuthenticationEvent() {

                    @Override
                    public void ifUserIsAdministrator() {
                        System.out.println("laure est admin, qui l'eu cru?");
                    }

                    @Override
                    public void onAuthenticate() {
                        UserRequester.modifyAccount(null, null, "Laure", new ModifyAccountEvent() {

                            @Override
                            public void errorAuthenticationRequired() {
                                System.out.println("authentification fail");
                            }

                            @Override
                            public void onModifyAccount() {
                                System.out.println("Laure modifié!");
                            }

                            @Override
                            public void errorUsernameDoesNotExist(String username) {
                                System.out.println("laure n'existe pas");
                            }
                        });
                    }

                    @Override
                    public void errorUserNotExists(String pseudo, String password) {
                        System.out.println("laure a pas pu être connecter");
                    }
                });
			}

			@Override
			public void errorUsernameAlreadyExists(String username) {
				System.out.println("laure existe déja");
			}
		});
	}

	public void retryWithNewLogin() {
		UserRequester.createUser("Laure28", "150m", "Manodou", "Laura", new CreateUserEvent() {

			@Override
			public void onCreateUser() {
				UserRequester.modifyAccount(null, null, "Laure", new ModifyAccountEvent() {

					@Override
					public void errorAuthenticationRequired() {
						System.out.println("authentification fail");
						retryWithAnotherLogin("Laure28", "150m");
					}

					@Override
					public void onModifyAccount() {
						System.out.println("Laure modifié!");
					}

					@Override
					public void errorUsernameDoesNotExist(String username) {
						System.out.println("laure n'existe pas");
					}

                    @Override
                    public void errorAdministratorOrOwnerRequired() {

                    }
                });
			}

			@Override
			public void errorUsernameAlreadyExists(String username) {
				System.out.println("laure existe déja");
			}
		});
	}

	public void listAllUsers() {
		UserRequester.createUser("Laure"+Math.random()*1500, "150m", "Manodou", "Laura", new CreateUserEvent() {

			@Override
			public void onCreateUser() {
				authenticate();
				UserRequester.listOfAllUser(new UsersListingEvent() {

					@Override
					public void onUsersReceived(ArrayList<User> users) {
						System.out.println("hello");
					}
				});
			}

			@Override
			public void errorUsernameAlreadyExists(String username) {
				authenticate();
				UserRequester.listOfAllUser(new UsersListingEvent() {

					@Override
					public void onUsersReceived(ArrayList<User> users) {
						System.out.println("hello");
					}
				});
			}
		});
	}

	/* --------------- */
}
