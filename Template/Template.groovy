package templates

class Template {
    static credentialsGitHub = 'github'
    static defaultBranchName = 'master'
    static defaultFileName = 'Jenkinsfile'
    static defaultIncludedBranches = ''
    static defaultExcludedBranches = ''
    static hostNameGitHub = 'git@github.com:Backbase/recruitment-devops-engineer-assignment.git'

    static void createPipelineJobWithTriggers(job, Map params = [:]) {
        def config = createConfig(hostNameGitHub, job, params)

        addDefWithTriggers(job, config, credentialsGitHub)
    }

    static void createPipelineJob(job, Map params = [:]) {
        def config = createConfig(hostNameGitHub, job, params)

        addDef(job, config, credentialsGitHub)
    }

    private static void addDefWithTriggers(job, config, creds) {
        job.with {
            definition {
                cpsScm {
                    scm {
                        git {
                            remote {
                                url("${config.hostName}/${config.repoName}.git")
                                credentials(creds)
                                name('origin')
                                refspec('+refs/heads/*:refs/remotes/origin/*')
                            }
                            branch('master')
                            extensions {
                                localBranch('**')
                                wipeOutWorkspace()
                            }
                        }
                    }
                    scriptPath("${config.fileName}")
                }
            }
            triggers {
                githubPush {
                    buildOnMergeRequestEvents("${config.buildOnMergeRequestEvents}".toBoolean())
                    buildOnPushEvents("${config.buildOnPushEvents}".toBoolean())
                    enableCiSkip(true)
                    targetBranchRegex("**")
                }
            }
        }
    }

    private static void addDef(job, config, creds) {
      job.with {
          definition {
              cpsScm {
                  scm {
                      git {
                          remote {
                              url("${config.hostName}/${config.repoName}.git")
                              credentials(creds)
                              name('origin')
                              refspec('+refs/heads/*:refs/remotes/origin/* +refs/merge-requests/*/head:refs/remotes/origin/merge-requests/*')
                          }
                          branch('**')
                          extensions {
                              localBranch('**')
                          }
                      }
                  }
                  scriptPath("${config.fileName}")
              }
          }
      }
    }

    private static def createConfig(hostName, job, params) {
        // Initialise configuration with defaults
        def config = [hostName        : hostName, repoName: job.name, branchName: defaultBranchName,
                      fileName        : defaultFileName, includedBranches: defaultIncludedBranches,
                      excludedBranches: defaultExcludedBranches]

        // "Override" default configured variables
        config.putAll(params)

        return config
    }
}
