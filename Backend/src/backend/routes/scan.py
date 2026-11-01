from pathlib import Path

from fastapi import APIRouter, UploadFile

from backend.paths import APPS_DIR


scan_router = APIRouter()



@scan_router.post("/scan")
async def upload_scan(file: UploadFile):
    APPS_DIR.mkdir(exist_ok=True)

    file_name = Path(file.filename).name

    contents = await file.read()
    (APPS_DIR / file_name).write_bytes(contents)

    return {"saved": file_name}
