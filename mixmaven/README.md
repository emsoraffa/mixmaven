# MixMaven

MixMaven is a project in the subject IT1901. This is the project of group 31.  The App lets a user store their drink recipes.
The app allows the user to:

- Add drinks to their library of drinks.
- Edit drinks in their library of drinks.
- Delete Drinks from their library of Drinks.

You will find the documentation for each release in the [docs](/docs/) folder or in the following link(s).

- [Release 1](/docs/release-1/)
- [Release 2](/docs/release-2/)
- [Release 3](/docs/release-3/)

Userstories can be found here:

- [Userstories](/mixmaven/userstories.md)  

 There is also documentation inside each module describing that module.

- [Core](/mixmaven/core/README.md)
- [Json](/mixmaven/json/README.md)
- [UI](/mixmaven/ui/README.md)
- [SpringBoot](/mixmaven/springboot/README.md)

## Build and running the project

### Build

The project is run on **maven version 3.9.4** and **java version 17.0.8**.

If you are in the root folder **GR2331** you need to move into the mixmaven folder. Do this with the following command:

```cmd
cd mixmaven
```

To build you need to run:

```cmd
mvn clean install -DskipTests
```

### Run

When running the App you have two options:

#### Running the app without a server

The app itself recognises that the server is not running and will initialize local storage instead.

In order to run the App without a server,  move into the **ui folder** and run <mark style="background-color: #7e7e7e; padding:3px; border-radius:2px"> mvn javafx:run</mark> , Like so:

```cmd
cd ui
mvn javafx:run
```

#### Running the app with a server

In order to run the App with a server, we need to first start the server.

Move into the springboot folder and run mvn spring-boot:run, like so:

```cmd
cd springboot
mvn spring-boot:run
```

Then go back and move into ui and run mvn javafx:run, like so:

```cmd
cd ../ui
mvn javafx:run
```

## Testing

In order to run the included tests, use the following command in the mixmaven folder:

```cmd
mvn test
```

### Test Coverage

We utilise JaCoCo to analyze our test coverage. This can be done by running

```cmd
mvn clean test
mvn jacoco:report
```

Then you can go in the corresponding folder (core/ui) and inside target/site open the index.html file in a browser

## The App

The app contains three primary pages.

### 1

 The first one is page where you can add drinks to your library of drinks. This page is accesible from **Add Drink** button on the upper taskbar. It looks as following:

![image](/docs/release-2/release-2-adddrink.png)

**The page allows the user to add drinks with the parameters:**

- Drink Name
- A List of Ingredients
  
**Where all ingredients consists of the parameters:**

- Ingredient name
- Alchohol Percentage
- Amount
- Unit, which is one of three:
  - ml
  - dl
  - gram
- Type, which is one of three:
  - alchohol
  - mixer
  - extras

Whenever an ingredient is added, the view to the left fills out with the ingredients and the user can delete ingredients from this view if desired.

### 2

 The second is a page where the user can view their Drinks. This is the default view when the app is ran and can otherwise be reached through the **Your Drinks** Button and looks as following:

![image](/docs/release-2/release-2-browsedrinks.png)

The page presents all the drinks added by the user in the order the user added the drinks. There are two options for the drinks. The user can delete the drink with the **Delete Drink** button or the user can edit a selected drink with the **Edit Drink** button, which sends the user to the final page.

### 3

The final page is a page where the user can edit a selected drink and is reached through the **Edit Drink** button on a drink in the second page. It looks as following:

![image](/docs/release-2/release-2-editdrink.png)

It shares many similarities with the **Add Drink** page, but differs in a few ways. When the user clicks on one of the ingredients in the listview to the left, the parameter fields will fill out with the corresponding data. The user is then free to do changes and by pressing the **Update Ingredient** button will apply the corresponding changes to the ingredient. Otherwise the functionality is the same as **Add Drink** and pressing the **Update Drink** button will change the Drink.

## The App is packageable

In order to package the App run the following commands in the mixmaven directory

```cmd
mvn javafx:jlink -f ./ui/pom.xml
mvn jpackage:jpackage -f ./ui/pom.xml
```
