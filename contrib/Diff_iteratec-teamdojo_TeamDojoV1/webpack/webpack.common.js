59,60c59,60
<                 BUILD_TIMESTAMP: `'${new Date().toISOString()}'`,
<                 VERSION: `'${process.env['TEAMDOJO_BUILD_VERSION'] || '_DEV'}'`,
---
>                 BUILD_TIMESTAMP: `'${new Date().getTime()}'`,
>                 VERSION: `'${utils.parseVersion()}'`,
83,84c83,84
<                     { pattern: "./src/main/webapp/i18n/de/*.json", fileName: "./i18n/de.json" },
<                     { pattern: "./src/main/webapp/i18n/en/*.json", fileName: "./i18n/en.json" }
---
>                     { pattern: "./src/main/webapp/i18n/en/*.json", fileName: "./i18n/en.json" },
>                     { pattern: "./src/main/webapp/i18n/de/*.json", fileName: "./i18n/de.json" }
