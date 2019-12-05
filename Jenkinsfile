pipeline {
    agent any

    stages {
        stage ('Compile Stage') {

            steps {
                
                withMaven(maven : 'maven 3.6') {
                    bat 'mvn clean compile'
                }
            }
        }

        stage ('Testing Stage') {
            steps {
                withMaven(maven : 'maven 3.6') {
                    bat 'mvn test'
                }
            }
        }


        stage ('Deployment Stage') {
            steps {
                withMaven(maven : 'maven 3.6') {
                     bat 'mvn clean install'
                }
            }
        }
    }
}
