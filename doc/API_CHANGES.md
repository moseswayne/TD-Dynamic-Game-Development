# API Changes

## Gideon Pfeffer (gap16)

### ActorGrid 

| Interface         | Classes That Implement It | Methods                                                                                                                                                                                                                                                                                                       |
|-------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ReadableGrid      | ActorGrid                 | Collection< Grid2D> getActorLocationsInRadius(double x, double y, double radius, BasicActorType type);  <br><br> Grid2D getLocationOf(int id); <br><br> Collection< Grid2D> getActorLocations(BasicActorType type); <br><br> boolean isValidLoc(double x, double y); <br><br> double getMaxX(); <br><br> double getMaxY(); <br><br> ListenQueue getEventQueue(); |
| ReadAndMoveGrid   | ActorGrid                 | extends ReadableGrid <br><br> void move(int ID, double newX, double newY);                                                                                                                                                                                                                                            |
| ReadAndSpawnGrid  | ActorGrid                 | extends ReadableGrid <br><br> void actorSpawnActor(Integer actorType, double startX, double startY, Consumer< Collection< IActProperty< MasterGrid>>> action);                                                                                                                                                                                               |
| ReadAndDamageGrid | ActorGrid                 | extends ReadableGrid <br><br> Map< Consumer< Double>, Double>getActorDamagablesInRadius(double x, double y,,double radius, BasicActorType type); <br><br> Consumer getMyDamageable(int actorID); <br><br> WriteableGameStatus getWriteableGameStatus();                                                                                                    |
| ReadShootMoveGrid | ActorGrid                 | extends ReadAndSpawnGrid, ReadAndMoveGrid                                                                                                                                                                                                                                               |
| MasterGrid        | ActorGrid                 | extends ReadShootMoveGrid, ReadAndDamageGrid                                                                                                                                                                                                                                            |

### ControllerGrid

| Interface        | Classes That Implement It | Methods                                                                                                                                                                                                                                 |
|------------------|---------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Steppable        | ActorGrid                 | void step();                                                                                                                                                                                                                            |
| ControllableGrid | ActorGrid                 | extends Steppable(); <br><br> void move(int ID, double newX, double newY); <br><br> void removeActor(int ID); <br><br> void controllerSpawnActor(Actor actor, double startX, double startY); <br><br> Grid2D getLocationOf(int ID); <br><br> boolean isValidLoc(double x, double y); |

### Grid Utilities and Handlers

| Interface            | Classes That Implement It | Methods                                                                          |
|----------------------|---------------------------|----------------------------------------------------------------------------------|
| GridHandler          | See GameController        | WriteableGameStatus getWriteableGameStatus(); <br><br> ListenQueue getEventQueue();    |
| FrontEndInformation  | DisplayInfo               | Grid2D getActorLocation(); <br><br> double getActorPercentHealth(); <br><br> int getActorOption(); |
| ActorLocator         | ActorFinder               | Actor getActor(); <br><br> Grid2D getLocation();                                          |
| SettableActorLocator | ActorFinder               | extends ActorLocator <br><br> void setLocation(double x, double y);                       |
| Grid2D               | Coordinates               | double getY(); <br><br> double getX();                                                    |

### XML and File Management

| Interface            | Classes That Implement It | Methods                                                                          |
|----------------------|---------------------------|----------------------------------------------------------------------------------|
| FileHelper |  ConcreteFileHelper | boolean overwriteStringFile(String filepath, String fileContent) throws IllegalFileException, IOException; <br><br> boolean deleteFile(File file); <br><br> boolean deleteDir(File directory) throws IOException; <br><br> String readFile(File file) throws IOException; <br><br> void moveFile(String startDirPath, String endDirPath, String filename) throws IllegalFileException, IOException;|
| DirectoryFileWriter | ExistingDirectoryHelper <br><br> NewDirectoryHelper | extends DirectoryFileReader <br><br> boolean addStringFileToDirectory(String fileContent, String filename) throws IOException, IllegalFileException; |
| DirectoryFileReader | ExistingDirectoryHelper <br><br> NewDirectoryHelper | boolean fileExists(String fileName); <br><br> String getFileContent(String filename) throws IOException, IllegalFileException; <br><br> Collection< String> getAllNewFilenames(); <br><br> Collection< String> getAllFilesInDirectory();|
| DirectoryFileManager | ExistingDirectoryHelper <br><br> NewDirectoryHelper | extends DirectoryFileWriter <br><br> void cleanse() throws IOException; |
| VoogaSerializer | XStreamSerializer | String getXMLStringFromObject(Object o); <br><br> < C> C makeObjectFromXMLString(String XMLString, Class< C> clazz) throws IllegalXStreamCastException;|

### Facebook

| Interface            | Classes That Implement It | Methods                                                                          |
|----------------------|---------------------------|----------------------------------------------------------------------------------|
| FacebookAuthenticator | Authenticator | boolean isAuthenticated(); <br><br> void authenticate();| 
| FacebookPoster | Authenticator | String postWithoutVoogaLink(String toPost); <br><br> String postWithVoogaLink(String toPost); | 
| ProfilePictureAcessor | Authenticator | Image getProfilePicture(); | 
| MasterFacebookUser | Authenticator | extends FacebookAuthenticator, FacebookPoster, ProfilePictureAccessor| 

### DirectoryTree 

**These classes were never finished and were not used anywhere in the project**

| Interface            | Classes That Implement It | Methods                                                                          |
|----------------------|---------------------------|----------------------------------------------------------------------------------|
| BoolSwitch | ConcreteBoolSwitch | void setTrue(); <br><br> void setFalse(); <br><br> boolean getState(); |
| Pair< K,V> | ConcretePair | K getKey(); <br><br> V getValue(); <br><br> void setKey(K k); <br><br> void setValue(V v); |
| StringDirectoryTree | DirectoryNode | boolean addDirectoryToTree(String filepath); <br><br> boolean addFileToTree(String filepath); <br><br> String getRootDirectory(); <br><br> Collection< DirectoryNode> getSubDirectories(); <br><br> Collection< String> getFiles(); <br><br> void printTree(); |



