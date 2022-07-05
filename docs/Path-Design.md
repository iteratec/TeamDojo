* Overview/Dashboard: `/`
  * Team Overview (living Room): 
    * `/teams/sec`
    * `/teams/sec?level=1600` (filter selected level)
    * `/teams/sec?badge=4600` (filter selected badge)
      * Team Skill Detail View
        * Shows all skills related to the chosen team (sec) and the corresponding skill details
        * A team can mark a skill as "completed" or "reopen" them directly
        * `teams/sec/skills/3300?` (selected skill 3300)
        * `/teams/sec/skills/1300?badge=4600`
        * `/teams/sec/skills/3300?level=&badge=`
  * Skill Overview: 
    * Shows all skills details
    * A team CANNOT marked as "completed" or "reopen" them directly, because the team relation is not present
    * `/overview/skills/2100`