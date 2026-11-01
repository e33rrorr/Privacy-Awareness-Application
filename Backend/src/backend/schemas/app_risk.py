from pydantic import BaseModel

# App risk schema
class AppRisk(BaseModel):
    package_name: str  
    risk: str         
