package com.synapsisid.smartdeskandroombooking.util


interface DataMapper<Entity,DomainModel> {
    fun entityToModel(entity:Entity):DomainModel

    fun modelToEntity(model:DomainModel):Entity

//    fun entityListToModelList(entities:List<Entity>):List<DomainModel>
}