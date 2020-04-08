package latheesh.com.dictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import latheesh.com.dictionary.R
import latheesh.com.dictionary.model.ResultData
import kotlinx.android.synthetic.main.dictionary_item.view.*

class DictionaryAdapter : RecyclerView.Adapter<DictionaryAdapter.ViewHolder>() {
    private var resultData: List<ResultData> = ArrayList()

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.dictionary_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        @NonNull holder: ViewHolder,
        position: Int
    ) {
        holder.run { bindItem(resultData[position]) }
    }

    override fun getItemId(position: Int): Long {
        return resultData[position].id
    }

    override fun getItemCount(): Int {
        return resultData.size
    }

    fun setData(data: List<ResultData>) {
        this.resultData = data
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindItem(item: ResultData) {
            itemView.apply {
                item.let {
                    item_word_textView.text = it.word
                    item_definition_textView.text = it.definition
                    item_thumbsDown_textView.text = it.thumbDown.toString()
                    item_thumbsUp_textView.text = it.thumbUp.toString()
                    item_author_textView.text =
                        context.getString(R.string.dictionary_author_text, it.author)
                    item_example_textView.text =
                        context.getString(R.string.dictionary_example_text, it.example)

                }

            }

        }
    }
}


