import { ILevel } from 'app/entities/level/level.model';

export const sortLevels = (levels: ILevel[] | undefined): ILevel[] => {
  if (!levels) {
    return [];
  }

  const sortedLevels: ILevel[] = [];
  if (levels.length > 0) {
    const rootLevelIndex = levels.findIndex((level: ILevel) => level.dependsOn === undefined || level.dependsOn === null);
    let currentLevel: ILevel | undefined = rootLevelIndex === -1 ? levels.pop() : levels.splice(rootLevelIndex, 1)[0];
    if (currentLevel) {
      sortedLevels.unshift(currentLevel);
    }

    while (levels.length > 0) {
      const nextLevelIndex = levels.findIndex((level: ILevel) => level.dependsOn?.id === sortedLevels[0].id);
      currentLevel = nextLevelIndex === -1 ? levels.pop() : levels.splice(nextLevelIndex, 1)[0];
      if (currentLevel) {
        sortedLevels.unshift(currentLevel);
      }
    }
  }
  return sortedLevels;
};
