from fastapi import APIRouter

from backend.schemas.permission_detail import PermissionDetail
from backend.services.risk_scoring.score_apps import load_knowledge_base


permissions_router = APIRouter()


@permissions_router.get("/permissions", response_model=dict[str, PermissionDetail])
def get_permissions():
    return load_knowledge_base()
