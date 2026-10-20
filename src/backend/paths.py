# Share a common location for storing uploaded app permission files.

from pathlib import Path

APPS_DIR = Path(__file__).resolve().parents[2] / "apps"
