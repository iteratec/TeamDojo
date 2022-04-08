# Architecture

In this document we describe the overall architecture and concepts of TeamDojo v2.

### Model

TeamDojo comes prefilled with some demo data (demo profile).

#### Topic

**XXX**: What is topic? Do we have this?

Next you would like to create some **Topics** your teams want to achieve skills for: _Entities - Topic_.
Examples could be _Quality Assurance_, _Security_, _Operations_, _Architecture_, ...

#### Team

Teams are the users of TeamDojo. Here you can create them.

_Entities - Team_

- **Name** - The full Team name.
- **Short Name** - An acronym of the Team's name. It will be part of the REST URL.
- **Picture** - The Team's logo
- Team **Slogan**
- Team **Contact Person**
- **Participations** - Every Team can participate on one or more Topics.

#### Level

Now you can specify maturity or ability **Levels** for these Topics.
Each Level should contain _n_ Skills - see next step.

A Level consists of the following settings:

_Entities - Level_

- Name - The level name. Judo belt ranks for example: yellow, orange, green, ...
- [OPTIONAL] **Description** of the level.
- A **Picture** to be shown. You can find some examples in [examples/images/level](examples/images/level).
  You can create your own icons, but for the best user experience get some help from some skilled UI/UX people.
- **Required Score** - A decimal value from 0 to 1. You can define how much percent of achieved skills are necessary to
  get this level. Default can be 1.0
- **Skill Score Multiplier** - A decimal value. Here you can specify how much bonus points the team can achieve with every
  skill of this level. Default can be 0.0. See [Scoring System](#scoring-system-and-balancing) for more details.
- [OPTIONAL] **Level Completion Bonus** - A numeric value. How much bonus points the team can achieve with the
  completion of this level. See [Scoring System](#scoring-system-and-balancing) for more details.
- **Topic** - Every Level must be assigned to one topic.
- [OPTIONAL] A level can **depends on** a previous level. E.g.: To reach Level 2, all skills of Level 2 and Level 1 must
  be completed.

#### Badge

While Level are the core of the maturity model, **Badges** can be used to push some specific skills or to reward well
performing teams.

_Entities - Badges_

- Name - The badge name. Technology specific or cool names for skill subsets. E.g.: AWS Badge, Docker Master,
  Always-up-to-date Badge, Password Ninja, ...
- [OPTIONAL] **Description** of the badge.
- A **Picture** to be shown. You can find some examples in [examples/images/badge](examples/images/badge).
  You can create your own icons, but for the best user experience get some help from some skilled UI/UX people.
- [NOT IMPLEMENTED] Available Until - not implemented yet.
- [NOT IMPLEMENTED] Available Amount - not implemented yet.
- **Required Score** - A decimal value from 0 to 1. You can define how much percent of achieved skills are necessary to
  get this badge. Default can be 1.0.
- **Skill Score Multiplier** - A decimal value. Here you can specify how much bonus points the team can achieve with
  every skill of this badge. Default can be 0.0. See [Scoring System](#scoring-system-and-balancing) for more details.
- [OPTIONAL] **Badge Completion Bonus** - A numeric value. How much bonus points the team can gain with the
  completion of this badge. See [Scoring System](#scoring-system-and-balancing) for more details.
- **Topics** - Every Badge can be assigned to one ore more topics.

#### Skill

The core element of this framework. Teams can obtain **skills**.

_Entities - Skill_

- **Skill Title** - Short and significant.
- **Description** - Is part of the skill details. Why is this skill useful? Why should a team aim to obtain this skill?
- **Implementation** - How can the team achieve this skill? Hard facts to configure/implement/learn something.
- **Validation** - How can the team tell they have achieved this skill? Hard criteria.
- [OPTIONAL] **Expiry Priod** - For future development. A skill expires after a period of time.
- [OPTIONAL] **Contact** - A person with know how relevant for this skill.
- **Score** - With every achieved skill, a team gains scores. A default value could be 1 for every skill.
- [DO NOT TOUCH] **Rate Score** - Users can vote for a skill (1-5 stars). This value should not be set in the admin view.
- [DO NOT TOUCH] **Rate Count** - Users can vote for a skill (1-5 stars). This value should not be set in the admin view.

#### Assign Skills to Level and Badges

Every Skill should be assigned to at least one Level or Badge:

- _Entities - Level Skills_
- _Entities - Badge Skills_

Here you can specify which skills are necessary to obtain a specific Level or Badge.

### Scoring System and Balancing

If a team completes a skill, its score will be added to the team's score.
With the Skill **Score** property you can value its costs / complexity / importance.

To honor the skill completion of specific levels or badges, you can adjust the **Level/Badge Score Multiplier**.
Every completed Skill will add its skill score multiplied with the Score Multiplier.
E.g.:

```text
Skill: TLS everywhere; Score: 10
Badge: Encryption Master; Score Multiplier: 2

#Completion of TLS everywhere will resulting in:
   10 (Skill Score)
+  20 (Skill Score x Score Multiplier)
=> 30 Points
```

You can also reward the completion of levels and badges. Therefore you can gain bonus points with **Level/Badge Completion Bonus**.
If the required percentage (**Required Score**) of skills for the Badge or Level is reached, the Completion Bonus will
be added to the Team scores. E.g.:

```text
Skill: TLS everywhere; Score: 10
Skill: Update your Systems; Score: 30
Level: Green; Required Score: 1.0; Level Completion Bonus: 100

#Completion of all skills will resulting in:
    10 (Skill Score)
+   30 (Skill Score)
+  100 (Completion Bonus)
=> 140 Points
```

You can combine them of course:

```text
Skill: TLS everywhere; Score: 10
Skill: Update your Systems; Score: 30
Level: Green; Required Score: 1.0; Score Multiplier: 1; Level Completion Bonus: 100
Badge: Encryption Master; Score Multiplier: 2


#Completion of all skills will resulting in:
    10 (Skill Score)
+   10 (Skill Score x Level Multiplier)
+   20 (Skill Score x Badge Multiplier)
+   30 (Skill Score)
+   30 (Skill Score x Level Multiplier)
+  100 (Level Completion Bonus)
=> 200 Points
```

## Security

For maximum transparency and accessibility there is no user / role concept.
Everyone can see and change everything. An exception is the technical Administrator.
He configures rarely changing data like teams, levels, skills, etc. to hide the internal complexity.

We believe that this tool helps software development teams to track where they are performing well and where they can
become improve to build better software.
Any additional complexity level - processes, login, role models - will result in a decreasing motivation to use
yet another tool during your daily business.

The easiest way to hide your internal data are IP whitelists on network level.
Host this tool in your internal network and let everyone use and see it.

If you really want any kind of authentication/authorization process, build it, push it back into this repository but
make it optional.
