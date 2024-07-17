# Automating CA Onboarding Application

## Table of Contents
- [Project Description](#project-description)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Project Description
The Automatic CA Onboarding Application is designed to automate the workflows involved in CA onboarding and provide respective performance dashboards. The application includes functionalities for user registration, CA form submission, UTM link generation, and email notifications.

## Features
1. **Login and Registration**
   - User registration with validation.
   - User credentials are stored in the database.

2. **CA Onboarding Form**
   - Submission of user details.
   - User details are stored in the database.

3. **UTM Link Generation**
   - Create UTM links for the Industry Academia Community website.
   - Send the generated UTM link to the user's email.

## Technologies Used
- Java
- Swing
- JavaFX
- JDBC for Database Connectivity
- Email Service (Jakarta Mail API)

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/CA-Onboarding-Application.git
    ```
2. Navigate to the project directory:
    ```bash
    cd CA-Onboarding-Application
    ```
3. Ensure you have Java installed. If not, download and install Java from [here](https://www.oracle.com/java/technologies/javase-downloads.html).
4. Setup your database and update the database connection details in the application.

## Usage
1. Compile and run the application:
    ```bash
    javac -d bin src/*.java
    java -cp bin Main
    ```
2. Follow the on-screen instructions to use the application.

## Contributing
Contributions are welcome! Please follow these steps to contribute:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact
For any inquiries, please contact:
- Name: Ben
- Email: ben@example.com

---

Thank you for using the Automatic CA Onboarding Application!
