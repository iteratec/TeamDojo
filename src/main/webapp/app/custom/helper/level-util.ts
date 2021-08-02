import { ILevel } from 'app/entities/level/level.model';

export const sortLevels = (levels: ILevel[]) => {
  const sortedLevels: ILevel[] = [];
  if (levels.length > 0) {
    const rootLevelIndex = levels.findIndex((level: ILevel) => level.dependsOn === undefined || level.dependsOn === null);

    // @ts-ignore
    sortedLevels.unshift(rootLevelIndex === -1 ? levels.pop() : levels.splice(rootLevelIndex, 1)[0]);
    while (levels.length > 0) {
      const nextLevelIndex = levels.findIndex((level: ILevel) => level.dependsOn?.id === sortedLevels[0].id);
      // @ts-ignore
      sortedLevels.unshift(nextLevelIndex === -1 ? levels.pop() : levels.splice(nextLevelIndex, 1)[0]);
    }
  }
  return sortedLevels;
};
