from pydantic import BaseModel

# Permission detail schema
class PermissionDetail(BaseModel):
    category: str     
    description: str  
    what_to_consider: str 
