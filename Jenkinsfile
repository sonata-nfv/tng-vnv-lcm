#!/usr/bin/env groovy
pipeline {
    agent any
    stages {
      stage('test') {
         steps {
            timestamps {
                sh './gradlew clean test'
            }
         }
      }
      stage('build') {
         steps {
            timestamps {
                sh './gradlew dockerBuild -x test'
            }
         }
      }
      stage('push') {
         when{
             branch 'master'
         }
         steps {
            timestamps {
                sh 'docker push registry.sonata-nfv.eu:5000/tng-vnv-lcm:latest'
            }
         }
      }
    }
    post {
        always {
            junit(allowEmptyResults: true, testResults: 'build/test-results/**/*.xml')
            publishHTML (target: [
                  allowMissing: true,
                  alwaysLinkToLastBuild: false,
                  reportDir: 'build/reports/tests/test',
                  reportFiles: 'index.html',
                  reportName: "Test Report"
            ])
        }
        success {
            emailext (
              subject: "SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
              body: """<p>SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
            recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
          }
        failure {
          emailext (
              subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
              body: """<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
              recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
    }
}