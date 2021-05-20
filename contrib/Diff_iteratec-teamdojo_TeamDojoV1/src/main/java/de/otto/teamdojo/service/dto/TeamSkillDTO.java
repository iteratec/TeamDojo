2,5d1
< 
< import de.otto.teamdojo.domain.enumeration.SkillStatus;
< 
< import java.io.Serializable;
7a4
> import java.io.Serializable;
35,36d31
<     private Integer skillExpiryPeriod;
< 
127,134d121
<     }
< 
<     public void setSkillExpiryPeriod(Integer skillExpiryPeriod) {
<         this.skillExpiryPeriod = skillExpiryPeriod;
<     }
< 
<     public SkillStatus getSkillStatus() {
<         return SkillStatus.determineSkillStatus(irrelevant, completedAt, skillExpiryPeriod);
