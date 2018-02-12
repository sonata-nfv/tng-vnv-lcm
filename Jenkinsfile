pipeline {
   agent any
   stages {
      stage('All In One Build') {
         steps {
            timestamps {
                sh 'export'
                sh 'ls -alth'
                sh './gradlew'
            }
         }
      }
   }
}