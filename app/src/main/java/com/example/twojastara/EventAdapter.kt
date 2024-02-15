import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twojastara.EventItem
import com.example.twojastara.databinding.EventItemBinding

class EventAdapter(private val context: Context, private val foodItemList:MutableList<EventItem>)
    : RecyclerView.Adapter<EventAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = EventItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val foodItem = foodItemList[position]
        holder.bind(foodItem)
    }

    override fun getItemCount(): Int {
        return foodItemList.size
    }


    class ItemViewHolder(ItemLayoutBinding:EventItemBinding)
        : RecyclerView.ViewHolder(ItemLayoutBinding.root){

        private val binding = ItemLayoutBinding

        fun bind(eventItem: EventItem){
            binding.eventName.text = eventItem.name
            binding.eventDesc.text = eventItem.desc
        }

    }
}