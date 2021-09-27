import { SkillSortPipe } from 'app/custom/shared/pipe/skill-sort.pipe';
import { Skill } from 'app/entities/skill/skill.model';

describe('SkillSortPipe', () => {
  const skillSortPipe = new SkillSortPipe();

  const skill1 = new Skill(1, 'SkillOne', null, null, null, null, null, 1, null, 1);
  const skill2 = new Skill(2, 'SkillTwo', null, null, null, null, null, 2, null, 2);
  const skill3 = new Skill(3, 'SkillThree', null, null, null, null, null, 3, null, 3);
  const skill4 = new Skill(4, 'SkillFour', null, null, null, null, null, 4, null, 4);
  const skill5 = new Skill(5, 'SkillFive', null, null, null, null, null, 5, 4, 5);
  const skill6 = new Skill(6, 'SkillSix', null, null, null, null, null, 6, 5, 6);

  const skillArrReversedId = [skill4, skill3, skill2, skill1];
  const skillArrOrderedId = [skill1, skill2, skill3, skill4];
  const skillArrOrderedTitle = [skill4, skill1, skill3, skill2];

  const skillArrShuffledRateCount = [skill3, skill1, skill4, skill2];
  const skillArrShuffledRateCountSortedByRateCount = [skill4, skill3, skill2, skill1];

  const skillArrRateCount = [skill6, skill3, skill5, skill4];
  const skillArrRateCountSorted = [skill3, skill4, skill5, skill6];
  it("should return the input array ordered by id property in ascending order if sortProperty is equal to 'id' ", () => {
    expect(skillSortPipe.transform(skillArrReversedId, 'id')).toEqual(skillArrOrderedId);
  });

  it('should return the input array ordered by title property in ascending order if' + " sortProperty is equal to 'title' ", () => {
    expect(skillSortPipe.transform(skillArrReversedId, 'title')).toEqual(skillArrOrderedTitle);
  });

  it('should return the input array ordered by rateCount in descending order, if sortProperty ' + "is equal to 'rateCount'", () => {
    expect(skillSortPipe.transform(skillArrShuffledRateCount, 'rateCount')).toEqual(skillArrShuffledRateCountSortedByRateCount);
  });

  it('should return the input array if sortProperty is null for every object contained in the input array ', () => {
    expect(skillSortPipe.transform(skillArrShuffledRateCount, 'rateScore')).toEqual(skillArrShuffledRateCount);
  });

  it(
    'should return the input array with every object where the sortProperty is null in front, followed by the remaining' +
      ' objects sorted',
    () => {
      expect(skillSortPipe.transform(skillArrRateCount, 'rateScore')).toEqual(skillArrRateCountSorted);
    }
  );
});
