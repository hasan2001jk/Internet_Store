# Internet_Store
This is a web-based project replicated from the official **`Manchester United`** website for my **`Samara State Transport University`** course. The website, **`manutd`**, provides a straightforward and efficient platform for fans to purchase merchandise while they are commuting. With an easily navigable interface, customers can explore a broad array of official goods and place an order with just a few clicks. Ideal for those who are constantly on the move and support the **Red Devils**.

## Prerequisites

1. Docker
2. Docker Compose
3. Maven
4. Terraform

Ensure these are installed on your system before proceeding.

## Steps

1. **Pull the latest version of the project from the GitHub repository.**
    
    ```
    git clone <https://github.com/hasan2001jk/Internet_Store.git>
    
    ```
    
2. **Navigate to the project directory.**
    
    ```
    cd Internet_Store
    
    ```
    
3. **Build the Java application using Maven.**
    
    This will generate the JAR file in the `target` directory.
    
    ```
    ./mvnw package -DskipTests
    
    ```
    
4. **Build the Docker images for the application and the database using the Dockerfiles provided.**
    
    Use the following commands to build the images:
    
    For the application:
    
    ```
    docker build -t my_app_img:v1 -f Dockerfile1 .
    
    ```
    
    For the database:
    
    ```
    docker build -t postgres:v1 -f Dockerfile2 .
    
    ```
    
5. **Run the Docker Compose command.**
    
    This will start the application and the database services.
    
    ```
    docker-compose up
    
    ```
    
6. **The application should now be running and accessible on the host machine.**
7. **Set up the infrastructure.**
    
    Ensure you have the right credentials set up for the `regru` provider. Update the `regru_server` and `regru_ssh` resources with the appropriate values.
    
    Run the following commands:
    
    To initialize your working directory containing Terraform configuration files:
    
    ```
    terraform init
    
    ```
    
    To create an execution plan:
    
    ```
    terraform plan
    
    ```
    
    To apply the changes required to reach the desired state of the configuration:
    
    ```
    terraform apply
    
    ```
    
    Remember to replace any placeholder values in the commands with your actual values.
