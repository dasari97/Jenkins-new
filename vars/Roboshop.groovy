def call (String Agent) {
    pipeline {

  agent {
    node {
      label 'Agent'
    }
  }

  stages {

    stage('Compiling the code') {
      steps {
        echo 'Code compilation done'
      }
    }

    stage('Check Code Quality') {
      steps {
        echo 'Checking Code Quality'
      }
    }

    stage('Lint Checks') {
      steps {
        echo 'Checking Lint Checks'
      }
    }

    stage('Unit Tests') {
      steps {
        echo 'Unit tests'
      }
    }

    stage('Prepare Artifact') {
      steps {
        sh '''
          cd static
          zip -r frontend.zip * 
        '''
      }
    }

    stage('Publish Artifacts') {
      steps {
        echo 'Publish Artifacts'
      }
    }

  }

}


}