MJM Ship Tracking System

    The MJM Ship Tracking System is a web application that allows users to track and monitor ships on a map. It provides real-time information about ship locations, routes, and other relevant details. The system consists of a backend server implemented in Java using the Spring Boot framework and a frontend interface built with HTML, CSS, and JavaScript.

Features

    Live ship tracking: The system retrieves ship data from various sources and displays their current positions on a map.
    Ship details: Users can view detailed information about individual ships, including their names, MMSI numbers, coordinates, and ship types.
    Ship routes: The system allows users to view the historical routes taken by ships.
    User management: Users can create accounts, log in, and manage their monitored ships.
    Ship monitoring: Users can add or remove ships from their list of monitored ships.
    Responsive design: The frontend interface is designed to work seamlessly on desktop and mobile devices.

Technologies Used

    Java
    Spring Boot
    Thymeleaf (Java-based templating engine)
    HTML/CSS/JavaScript
    Bootstrap (CSS framework)
    Leaflet (JavaScript library for interactive maps)
    MySQL (Database for storing ship and user data)

Installation

    Clone the repository: git clone https://github.com/your/repository.git
    Navigate to the project directory: cd project-directory
    Configure the database connection in the application.properties file.
    Build the project: mvn clean package
    Run the application: java -jar target/mjm-ship-tracking-system.jar
    Open a web browser and visit http://localhost:8080 to access the application.

Usage

    Upon accessing the application, you will be presented with a map displaying the live positions of ships.
    You can click on a ship marker to view more details about the ship, including its name, MMSI number, and coordinates.
    To monitor a ship, click the "Add" button in the ship's popup. To stop monitoring a ship, click the "Remove" button.
    You can view a list of all ships and their details by navigating to the "All Ships" page.
    The "Routes" page allows you to view historical routes taken by ships.
    User authentication is implemented, so you need to create an account and log in to monitor ships and access certain features.

Contributing

    Contributions to the MJM Ship Tracking System are welcome! If you find any issues or have suggestions for improvements, please submit a pull request or open an issue on the project's GitHub repository.

License

    This project is licensed under the MIT License.
