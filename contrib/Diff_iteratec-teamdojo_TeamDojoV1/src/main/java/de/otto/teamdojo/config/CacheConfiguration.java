42a43,48
>             cm.createCache(de.otto.teamdojo.domain.Activity.class.getName(), jcacheConfiguration);
>             cm.createCache(de.otto.teamdojo.domain.Badge.class.getName(), jcacheConfiguration);
>             cm.createCache(de.otto.teamdojo.domain.Badge.class.getName() + ".skills", jcacheConfiguration);
>             cm.createCache(de.otto.teamdojo.domain.Badge.class.getName() + ".dimensions", jcacheConfiguration);
>             cm.createCache(de.otto.teamdojo.domain.BadgeSkill.class.getName(), jcacheConfiguration);
>             cm.createCache(de.otto.teamdojo.domain.Comment.class.getName(), jcacheConfiguration);
44d49
<             cm.createCache(de.otto.teamdojo.domain.Dimension.class.getName() + ".participants", jcacheConfiguration);
46a52,58
>             cm.createCache(de.otto.teamdojo.domain.Dimension.class.getName() + ".participants", jcacheConfiguration);
>             cm.createCache(de.otto.teamdojo.domain.Image.class.getName(), jcacheConfiguration);
>             cm.createCache(de.otto.teamdojo.domain.Level.class.getName(), jcacheConfiguration);
>             cm.createCache(de.otto.teamdojo.domain.Level.class.getName() + ".skills", jcacheConfiguration);
>             cm.createCache(de.otto.teamdojo.domain.LevelSkill.class.getName(), jcacheConfiguration);
>             cm.createCache(de.otto.teamdojo.domain.Organization.class.getName(), jcacheConfiguration);
>             cm.createCache(de.otto.teamdojo.domain.Report.class.getName(), jcacheConfiguration);
48d59
<             cm.createCache(de.otto.teamdojo.domain.Skill.class.getName() + ".teams", jcacheConfiguration);
50a62,63
>             cm.createCache(de.otto.teamdojo.domain.Skill.class.getName() + ".teams", jcacheConfiguration);
>             cm.createCache(de.otto.teamdojo.domain.Skill.class.getName() + ".trainings", jcacheConfiguration);
52d64
<             cm.createCache(de.otto.teamdojo.domain.Team.class.getName() + ".participations", jcacheConfiguration);
53a66
>             cm.createCache(de.otto.teamdojo.domain.Team.class.getName() + ".participations", jcacheConfiguration);
55,66d67
<             cm.createCache(de.otto.teamdojo.domain.Level.class.getName(), jcacheConfiguration);
<             cm.createCache(de.otto.teamdojo.domain.Level.class.getName() + ".skills", jcacheConfiguration);
<             cm.createCache(de.otto.teamdojo.domain.Badge.class.getName(), jcacheConfiguration);
<             cm.createCache(de.otto.teamdojo.domain.Badge.class.getName() + ".skills", jcacheConfiguration);
<             cm.createCache(de.otto.teamdojo.domain.Badge.class.getName() + ".dimensions", jcacheConfiguration);
<             cm.createCache(de.otto.teamdojo.domain.BadgeSkill.class.getName(), jcacheConfiguration);
<             cm.createCache(de.otto.teamdojo.domain.LevelSkill.class.getName(), jcacheConfiguration);
<             cm.createCache(de.otto.teamdojo.domain.Organization.class.getName(), jcacheConfiguration);
<             cm.createCache(de.otto.teamdojo.domain.Report.class.getName(), jcacheConfiguration);
<             cm.createCache(de.otto.teamdojo.domain.Comment.class.getName(), jcacheConfiguration);
<             cm.createCache(de.otto.teamdojo.domain.Activity.class.getName(), jcacheConfiguration);
<             cm.createCache(de.otto.teamdojo.domain.Image.class.getName(), jcacheConfiguration);
69d69
<             cm.createCache(de.otto.teamdojo.domain.Skill.class.getName() + ".trainings", jcacheConfiguration);
