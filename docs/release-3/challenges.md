# Challenges

## React and Eclipse Che

We had one more challenge during release 3. This being an issue between react and eclipse che.

The react app can be ran in eclipse che [Link to how](/mixmaven/README.md#eclipse-che). The issue is that the react application is not able to retrieve data from the server. We tried extensively to resolve this, but TAs were not able to assist and there was no sucess contacting faculty members and/or coordinator for IT1901.

### Trying to solve the challenge

The group tried extensively to troubleshoot the issue to no avail. We tried changing the .devcontainer and devfile.yaml, seeing if adding aditional endpoints or forwarding ports would help. It did not.

We believe the issue possibly lies with the configuration of the eclipse che work space. Through troubleshooting we found that when the react app is booted in eclipse che, it is not ran in the container but rather locally on the users computer.

### Makeshift Solution

The makeshift solution we came up with was to hard code the base URL to the server. We wil go through how to do this.

In eclipse che, first boot the spring boot server, then eclipse che will come with a prompt like this:
![Alt text](/docs/release-3/images/che-spring-boot-prompt.png)

Click `Open in new Tab` and this will take you to a new tab. Copy the url.

Then go into root directory of `webapp` and create a `.env` file. With the line `VITE_CHE_LINK` = {copied url}.

### Run react in eclipse che

In order to run the react application in eclipse che, open the [workspace](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2331/gr2331?new). Then open a terminal and follow the [procedure](/webapp/README.md#running-the-app):

Then eclipse che will come with a prompt similar to this one:
![Alt text](/docs/release-3/images/eclipse-che-redirect.png)

Click yes and open the redirected endpoint. Now the react application is running, and if the makeshift solution was implemented the react application will have access to the server.
