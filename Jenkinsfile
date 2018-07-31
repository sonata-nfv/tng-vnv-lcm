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
            steps {
                timestamps {
                    sh 'docker push registry.sonata-nfv.eu:5000/tng-vnv-lcm:v4.0'
                    sh 'docker tag registry.sonata-nfv.eu:5000/tng-vnv-lcm:latest registry.sonata-nfv.eu:5000/tng-vnv-lcm:v4.0'
                }
            }
        }
        stage('Deployment in Staging') {
          parallel {
            stage('Deployment in Staging') {
              steps {
                echo 'Deploying in Staging...'
              }
            }
            stage('Deploying') {
              steps {
                sh 'rm -rf tng-devops || true'
                sh 'git clone https://github.com/sonata-nfv/tng-devops.git'
                dir(path: 'tng-devops') {
                    sh 'ansible-playbook roles/vnv.yml -i environments -e "target=sta-vnv-v4.0 component=lcm"'
                }
              }
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