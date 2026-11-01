import uvicorn
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from backend.routes.permissions import permissions_router
from backend.routes.policy_summary import policy_summary_router
from backend.routes.risk_scoring import risk_router
from backend.routes.scan import scan_router

app = FastAPI()

app.include_router(risk_router)
app.include_router(scan_router)
app.include_router(permissions_router)
app.include_router(policy_summary_router)

origins = [
    "http://localhost",
    "http://localhost:8000",
]



app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)