package com.example.learningandroidpagination.model

import androidx.paging.PagingSource
import com.example.learningandroidpagination.api.ApiService
import com.example.learningandroidpagination.model.DoggoImagesRepository.Companion.DEFAULT_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

/**
 * PagingSource is the way to go here since we are going to
 * use remote network API as the data source.
 * so let’s create a class DoggoImagePagingSource and
 * implement PagingSource like below. Here I have passed
 * the DoggoApiService which we have created earlier
 * so that our PagingSource can call our doggo API and
 * get results. Apart from this while inheriting from
 * PagingSource we need to define the type of paging our
 * API supports in our case it is simple Int based number
 * paging and the return type of the API response that is
 * DoggoImageModel. Now we have implemented the PagingSource
 * let’s dig and get familiar with the load function.
 */
class DoggoImagePagingSource(private val apiService: ApiService) : PagingSource<Int, DoggoImageModel>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DoggoImageModel> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = apiService.getDoggoImages(page, params.loadSize)
            LoadResult.Page(
                response, prevKey = if(page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if(response.isEmpty()) null else page + 1
            )
        } catch (e: IOException){
            return LoadResult.Error(e)
        } catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }
}

/*
* params: Keeps the basic information related to the current page for which API needs to be called and the page size.
*
 */