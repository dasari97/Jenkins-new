def call (String COMPONENT) {
    pipeline {

  agent {
    node {
      label "JAVA"
    }
  }
  environment {
      Sonar_Token = credentials('Sonar_Token')
  }
  stages {
    stage('Submitting for code Compilation') {
        steps {
            sh 'mvn compile'
        }
    }

    stage('Checking Code Quality') {
      steps { 
        sh """ sonar-scanner -Dsonar.java.binaries=target/.  -Dsonar.projectKey=${COMPONENT} -Dsonar.sources=. -Dsonar.host.url=http://172.31.25.74:9000 -Dsonar.login=${Sonar_Token} """
      }
    }

    stage('Checking for Lints ') {
      steps {
        echo 'Checking Lint Checks'
      }
    }

    stage('Unit Tests') {
      steps {
        echo 'Unit tests'
      }
    }

    stage('Preparing Artifact') {
      steps {
        sh """
          cd static
          zip -r ${COMPONENT}.zip * 
        """
      }
    }

    stage('Publishing Artifacts') {
      steps {
        echo 'Publish Artifacts'
      }
    }

  }

  post {
    always {
      cleanWs()
    }
  }
}


}