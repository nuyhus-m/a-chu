import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.product.ProductResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


private const val TAG = "RecommendViewModel_안주현"
class RecommendViewModel : ViewModel() {

    private val _recommendMap = MutableStateFlow<Map<Int, List<ProductResponse>>>(emptyMap())
    val recommendMap: StateFlow<Map<Int, List<ProductResponse>>> = _recommendMap

    fun getRecommendList(babyId: Int) {
        viewModelScope.launch {
            productRepository.getRecommendedItems(babyId).onSuccess {
                val updatedMap = _recommendMap.value.toMutableMap().apply {
                    put(babyId, it.data)
                }.toMap() // ✅ 새로운 Map으로 명확하게 교체

                _recommendMap.value = updatedMap

                Log.d(TAG, "getRecommendList($babyId): 성공 ${it.data}")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "getRecommendList($babyId): $errorResponse")
            }
        }
    }



    fun likeItem(productId: Int){
        viewModelScope.launch {
            productRepository.likeProduct(productId).onSuccess {
                Log.d(TAG, "likeItem: ${it}")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "likeItem: ${errorResponse}")
            }

        }
    }

    fun unlikeItem(productId: Int){
        viewModelScope.launch {
            productRepository.unlikeProduct(productId).onSuccess {
                Log.d(TAG, "unlikeItem: ${it}")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "unlikeProduct: ${errorResponse}")
            }

        }
    }
}
