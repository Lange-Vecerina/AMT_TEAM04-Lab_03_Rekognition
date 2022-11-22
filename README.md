# AMT_TEAM04_Lab_03_Rekognition

**Authors : Yanik Lange, Ivan Vecerina**

## Introduction


Hello there ! 

Welcome to AMT 2022 Project Rekognition.
The following repository contains a custom API for labels detection.
The API will be easily adapted to different cloud Services in the future without impacting the user experience
(Amazon Rekogntion, Cloud Vision, Azure...). At this point it runs with Amazon Rekognition.

At this point our application can be run on a terminal by passing the path of the image you wish the get the labels 
from.

## Dependencies

The following prerequisite are necessary to use the project.

* Java 11 or bigger
* Maven
* AWS CLI
* A S3 bucket and AWS credentials

> TODO ajouter les commandes pour récupérer les dépendances

## Adapt personal settings

To run the application with your own AWS credentials you need to add the following credentials as environnement path of 
your machine :

Variable : **AWS_ACCESS_KEY_ID** Value : **<your_aws_access_key>**

Variable : **AWS_SECRET_KEY** Value : **<your_aws_secret_key>**

Example how to set environment variable on windows :
https://docs.oracle.com/en/database/oracle/machine-learning/oml4r/1.5.1/oread/creating-and-modifying-environment-variables-on-windows.html#GUID-DD6F9982-60D5-48F6-8270-A27EC53807D0

> TODO ajouter les commandes pour compiler, ainsi que quelque explications sur comment et pourquoi vous utilisez Maven 

> TODO ajouter la commande sur comment lancer un ou tout les tests 


## Run the Project on your machine

Now that you have set up your AWS credentials you can run the application.


### Run on your machine

Download the latest **.jar** release on the **Release** section in this github repository.

Open a *cmd/terminal* and go the directory where you saved your downloaded **.jar**.

To run the program enter the following command : 

```java -jar lab3_rekognition-v1-shaded.jar "<your_path_of_the_image_you_want_to_label>"```

Example of a **fruits.jpg** image in ```D:/image/``` folder :

```java -jar lab3_rekognition-v1-shaded.jar "D:/image/fruits.jpg"```

This will generate 2 temporary links to the bucket :

* The first links to the bucket where the image you asked to be labeled by Amazon Rekognition is.
* The second links to the result of the labeling (in the bucket too).


### Run on AWS instance (for review purpose)

Once connected to our instance through ssh. You will find our **.jar** file and a car image in the ```/home/admin/test``` folder.
In there you can run our app like above :

```java -jar lab3_rekognition-v1-shaded.jar "cars.jpg"```

## Run the tests

// TODO todo

[WIP]


## Backlog

[Backlog](https://github.com/orgs/Lange-Vecerina/projects/2)
