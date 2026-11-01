# Finds the web address of an app's original privacy policy.

import json
from pathlib import Path


# The policy_links folder next to this file.
POLICY_LINKS_DIR = Path(__file__).resolve().parent / "policy_links"


def load_policy_link(package_name):
    link_file = POLICY_LINKS_DIR / (Path(package_name).name + ".json")

    # None means no link has been saved for this app.
    if not link_file.exists():
        return None

    links = json.loads(link_file.read_text(encoding="utf-8"))
    return links[0]
