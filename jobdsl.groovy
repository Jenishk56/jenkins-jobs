Template.createPipelineJobWithTriggers(pipelineJob('coa-cwa-feature-branch'),
    [repoName: 'coa/coa-cwa', fileName: 'Jenkinsfile.patchset',  targetBranchRegex: '^feature\\/.*',
    buildOnMergeRequestEvents : true, buildOnPushEvents: true])
