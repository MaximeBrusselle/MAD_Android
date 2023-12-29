//package com.example.musicbrain.fake
//
//import com.example.musicbrain.data.TasksRepository
//import com.example.musicbrain.model.Task
//import com.example.musicbrain.network.asDomainObjects
//
//class FakeApiTasksRepository : TasksRepository {
//    override suspend fun getTasks(): List<Task> {
//        return FakeDataSource.tasks.asDomainObjects()
//    }
//}
