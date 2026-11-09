from pathlib import Path

from fastapi import APIRouter, UploadFile


from backend.paths import APPS_DIR

scan_router = APIRouter()


@scan_router.post("/scan")
async def upload_scan(file: UploadFile):

    file_name = Path(file.filename).name

    # The apps folder is git-ignored, so it won't exist on a fresh checkout.
    APPS_DIR.mkdir(parents=True, exist_ok=True)

    contents = await file.read()
    (APPS_DIR / file_name).write_bytes(contents)



