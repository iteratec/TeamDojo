# sed 's/new \([^(]*\)(\([^)]*\))/New\1{\2}/g'


ENTITY_NAMES = ["Activity",
                "Badge",
                "BadgeSkillComment",
                "Dimension",
                "Image",
                "Level",
                "LevelSkillReport",
                "Skill",
                "Team",
                "TeamSkill",
                "Training",
                "TeamGroup",
                "PersistentAuditEvent",
                "PersistentAuditEventData"]

from os import walk
import os
import re

def get_files_to_edit():
    for (dirpath, dirnames, filenames) in walk("."):
        for file in filenames:
            if file.endswith('.ts'):
                yield os.path.join(dirpath, file)


def substitute(matchobj):
    # print("group1:",matchobj.group(0), "group2", matchobj.group(1), "group3", matchobj.group(2))
    return "".join(['New', matchobj.group(1), "{", matchobj.group(2), "}"])

def edit_file(filepath):
    with open(filepath, 'r+') as f:
        filecontent = f.readlines()
        str = "".join(filecontent)
        for entity in ENTITY_NAMES:
            str = re.sub(r'new ({})[\s]*\(([^)]*)\)'.format(entity), substitute, str)
        f.seek(0)
        f.truncate(0)
        f.write(str)


def migrate_entity_instanciations():
    for file in get_files_to_edit():
        edit_file(file)

if __name__ == "__main__":
    migrate_entity_instanciations()
