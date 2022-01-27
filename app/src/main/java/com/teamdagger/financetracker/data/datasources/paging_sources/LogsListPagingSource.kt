package com.teamdagger.financetracker.data.datasources.paging_sources

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.synapsisid.smartdeskandroombooking.util.Util
import com.teamdagger.financetracker.data.datasources.local.FinanceDao
import com.teamdagger.financetracker.data.models.LogsTableMapper
import com.teamdagger.financetracker.domain.entities.Logs
import org.json.JSONObject

class LogsListPagingSource(
    private val context: Context,
    val financeDao: FinanceDao,
    val month: Int,
    val year: Int
) : PagingSource<Int, Logs>(){

    companion object{
        const val NETWORK_PAGE_SIZE = 10
    }

    override fun getRefreshKey(state: PagingState<Int, Logs>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Logs> {
        val position = params.key ?: 1

        return try{
            var data = financeDao.getLogsInMonth(month, year, (position-1)*10)
            val nextKey:Int?= if(data.isEmpty()) null else position + 1

            if(data!=null){
                var convertedData = data.map { LogsTableMapper.entityToModel(it) }.toList()
                Log.w("SYNPSS", convertedData.size.toString())

                LoadResult.Page(
                    data = convertedData,
                    prevKey = if (position == 1) null else position-1,
                    nextKey = nextKey
                )
            }else{

                LoadResult.Page(
                    data = listOf(),
                    prevKey = if (position == 1) null else position-1,
                    nextKey = nextKey
                )
            }

        }catch (e:Exception){
            LoadResult.Error(
                Exception("Error, there is something wrong")
            )
        }
    }
}