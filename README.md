# INSTRUCTIONS TO RUN AND DEPLOY THIS PROJECT

## Create an Amazon RDS instance
- Go to 'RDS’ under the AWS Console and Click ‘Create Database’
    - As the engine type, choose ‘MySQL’
    - As the template, choose ‘Free tier’ 
    - Under Settings, create a DB instance identifier, Master username, and Master Password. 
    - Under DB instance class, choose ‘db.t2.micro’
    - Under Public Access, make sure to click ‘Yes’
    - Under VPC Security Group, click ‘Create new’ and give it any name
    - Click Additional configuration, under ‘Initial database name, enter the DB instance identifier. 
- Once the instance has been made, click on the database. Under the ‘Connectivity & Security’ tag, click the link under ‘VPC security groups’
    - Click the VPC you created, and then click the ‘Inbound rules tag’. 
    - Click the ‘Edit inbound rules’ button 
    - Set the Type value to ‘All Traffic 
    - Under Source type, click ‘Anywhere-IPv4’
    - Click ‘Save rules’

## Connect Eclipse to your MySQL instance 
- Download the MySQL connector jar: https://dev.mysql.com/downloads/connector/j/
- Open your Eclipse and select Window -> Perspective -> Open Perspective -> Other -> Database Development 
- On the right side, right click ‘Database Connections’ and click ‘New’
    - Click ‘MySQL’ and click next
    - Click the circular symbol next to the driver dropdown 
    - Under Name/Type, click the highest version of ‘MySQL JDBC Driver’ from the list
    - Under Jar List, click add JAR/Zip and add the jar you downloaded. 
    - Go ahead and delete the other jar in the list so only the jar you downloaded is there, then click ‘ok’ 
- A Screen should show with database, url, user name, and password 
    - Update the database, user name, and password with the ones you created when you created the Amazon RDS instance. Go ahead and click save password for convenience. 
    - Update the URL in the format ‘jdbc:mysql://<*Endpoint*>:<*Port*>/<*Database-Name*>. Your endpoint can be found under the ‘Connectivity & security’ tag under your database on the RDS console. 
    - Click ‘Test Connection’ to make sure it works. If you get a dialog that says ‘Ping succeeded!’, then you have successfully set up the database and you can go ahead and click ‘Finish’ 
    - Go To Window -> Perspective -> Open Perspective -> Web
    - Use these values to update the values in your persistence.xml file. 

## Deploy the applications 
- Containerize and deploy the backend application first, studentsurvey-api (Steps on how to containerize and deploy applications available below)
- Once you’ve deployed the application and have received the endpoint, update the value of the url variable in both the survey/survey.component.ts file and the surveylist/surveylist.component.ts file, both available under studentsurvey-frontend/src/app. Make sure to save those files. 
- Containerize and deploy the backend application, studentsurvey-frontend (Steps on how to containerize and deploy applications available below)
    **NOTE:** To deploy the application, the app was containerized using docker and then it was deployed to the Rancher Cluster in the same cluster that the backend was deployed. You can even work in the same project, but both deployments must have different namespaces and different ports. In the case of this application, the backend was deployed on port 8080 and the frontend was deployed on port 80.


# CONTAINERIZE AND DEPLOY APPLICATION INSTRUCTIONS

## Containerize the application using Docker
- Install Docker for the OS that you’re currently using and make sure you have an account made on dockerhub online. 
- In your IDE, create a dockerfile. Ensure that the file is named ‘Dockerfile’ or docker will not recognize it. Make sure to also create the dockerfile in the same folder as your war (for an eclipse project) or as your build directory (for an angular project).
- Add the following lines in the Dockerfile based on the application 
    - For a War:
    ```
	FROM tomcat
	COPY <War-Name>.war /usr/local/tomcat/webapps/ (Substitute the name of your own war)
    ```
    - For an Angular Application:
    ```
    FROM node:latest as node
    WORKDIR /app
    COPY . .
    RUN npm install
    RUN npm run build --prod
    FROM nginx:alpine
    COPY --from=node /app/dist/<Application-name> /usr/share/nginx/html (Substitute the name of your application)
    ```
- On the command line, run the following command
    - 
    ```
    docker build -t <Image-Name>:0.1 .
    ```
    **NOTE:** Don’t forget the period at the end
- If you want to see the running application, run 
    ```
    docker run -it -d -p 8888:8080 <Image-Name>:0.1
    ```
    - Open http://localhost:8888/<*War-Name*>/
- Rename the tagged image to <*username*>/<*Image-Name*>. You can run:
    ```
    docker tag <Image-Name>:0.1 <username>/<Image-Name>:0.1
    ```
- Use the docker application to push the image to dockerhub by going to Images -> hover over the image until you see the three dots -> ‘Push to hub’ 
- Log into docker online and make sure you’re able to see the image you created 

## Set up Rancher 
- Create an AWS EC2 Instance that has docker running. In this case, I chose an Ubantu instance with t2.medium  
    - Make sure to add Http access on port 80 and Https access on port 443 
- Log into your instance using terminal from the same folder as the pem file
    ```
    ssh -i <pem-file-name> ubuntu@<public-IP-address>
    ```
- Download docker onto the instance by using: 
    ```
    sudo apt-get update
    sudo apt install docker.io 
    ```
- Run Rancher using 
    ```
    sudo docker run -d --restart=unless-stopped -p 80:80 -p 443:443 --privileged rancher/rancher:latest
    ```
- Open your public IP address and pass all errors 
- For the first time, create a new password following the steps that rancher gives you. 

## Create new IAM User 
**NOTE:** If you’ve already done this step before, you can just use the previously created IAM User
- Find ‘IAM’ in the AWS Console
- Click ‘Users’, then click the ‘Add users’ button
- Create a username and click the check mark next to ‘Access key - Programmatic access’ and click Next
- Click ‘Attach existing policies directly’ and click ‘AdministratorAccess’, then click Next until you get to create user. Make sure to save the access key and the secret access key using the ‘save csv’ button. 

## Create IAM Role 
**NOTE:** If you’ve already done this step before, you can just use the previously created IAM Role
- Find ‘IAM’ in the AWS Console
- Click ‘Roles’, then click the ‘Create Role’ button
- Click ‘AWS Service’ and Choose EC2 and click Next 
- Click ‘Create Policy’
    - Click the JSON tab and enter in this JSON (NOTE: make sure to fix formatting) and click next until you get to the review page:
    ```
    {
        "Version": "2012-10-17",
        "Statement": [
            {
                "Effect": "Allow",
                "Action": "*",
                "Resource": "*"
            }
        ]
    }
    ```
    - Give the policy any name and click ‘Create Policy’
- Refresh the permissions page using the refresh button and click on the newly created policy, then click next 
- Give your role a name and then create ‘Create role’

## Starting a Rancher Cluster
- Log into Rancher and go to ‘Cluster Management’ and click ‘Create’ 
- Click Amazon EC2 
- Give your cluster a name, enter a name prefix, check ‘etcd’, ‘Control Plane’ and ‘Worker’. Then, click ‘Add Template’ 
    - Under Account Access, choose the closest region, add the access ID and secret access key from the IAM user you created and give it a name, then click next 
    - Under Zone and Availability, choose a close Availability Zone and choose the vpc
    - Under Security Groups, select Standard
- Under Instance Options, Select ‘t2.medium’ and under ‘IAM Instance Profile Name’, enter the name of the IAM ROLE you created earlier. Everything else will be default. Give the template a name and then click ‘Create’ 
- Under Kubernetes Options, click ‘Amazon’ under Cloud Provider. Everything else will be default. Then, click ‘Create’. 
- Give the clusters a few minutes to start. If an error is thrown, stop the rancher server in your EC2 instance using ‘sudo docker stop <*container-name*>’ and follow the steps from *SET UP RANCHER* section again 

## Deploying the Image to the Rancher Cluster 
- Next to the name of your cluster, click ‘Explore’, then click ‘Projects/Namespaces’ on the side. 
- Click ‘Create Project’ and give your project a name 
- Click ‘Create Namespace’ next to that project and give the namespace a name
- Then click ‘Workload’ on the side, click ‘Create’, then choose ‘Deployment’ 
- Choose the namespace you just created, create a name, and set the number of replicas to 3. 
    -Under Image, enter the image name to the name of the image you created earlier, that's available on dockerhub. 
    - Under Ports, click ‘Add Port’. Choose ‘Load Balancer’, create some name, for both the ‘Private Container Port’ and the ‘Listening Port’ use port 8080 or 80. After this is done, click ‘Create’ 
    - Once the deployment says ‘Active’, click on the endpoint url (Ex. TCP/8080) and add the extension used to access the webapp in the localhost (Ex. /<*War-Name*>) and you should see the webpage! 

# HELPFUL ISSUE SOLUTIONS 
###	Ran into a CORS issue, causing the frontend to not reach the restful endpoint 
Created CorsFilter.java which extends ContainerResponseFilter, and overrided the filter() method to allow for access from anywhere (https://www.baeldung.com/cors-in-jax-rs)
###	Ran into a jar issue where the Hibernate jars were not compatible with JAX-RS jars  
Used Hibernate version 5.6.5 with JAX-RS version 3 since they both use Jakarta instead of javax
###	Ran into issue with Docker where the docker image for the backend was not correctly being created 
Specified the tomcat and jdk version in the docker file (tomcat:10.0.20-jdk17-corretto)
###	Ran into an issue where the information was not parsing correctly when calling the post method 
Forced use of x-www-form-urlencoded format for post request (https://stackoverflow.com/questions/39863317/how-to-force-angular2-to-post-using-x-www-form-urlencoded) 

# HELPFUL RESOURCES:
https://medium.com/codex/dockerize-angular-application-69e7503d1816
https://www.tektutorialshub.com/angular/angular-http-error-handling/
https://stackoverflow.com/questions/39863317/how-to-force-angular2-to-post-using-x-www-form-urlencoded
https://www.baeldung.com/cors-in-jax-rs
