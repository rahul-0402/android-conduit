package com.rahulghag.conduit.ui.articles.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rahulghag.conduit.common.CircularTextDrawable
import com.rahulghag.conduit.common.TextColorGenerator
import com.rahulghag.conduit.databinding.ItemArticleBinding
import com.rahulghag.conduit.domain.models.Article


class ArticleAdapter(
    private var list: List<Article>,
    private val onArticleClick: (String) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                val generator = TextColorGenerator.MATERIAL
                val color = generator.getColor(this.author.username)
                val circularTextDrawable = CircularTextDrawable.builder().round()
                val drawable = circularTextDrawable.build(
                    this.author.username.uppercase()[0].toString(), color
                )
                binding.imageViewAuthorAvatar.setImageDrawable(drawable)

                binding.textViewAuthorName.text = this.author.username

                binding.textViewPublishedDate.text = this.createdAt

                binding.textViewArticleTitle.text = this.title

                binding.textViewArticleDescription.text = this.description

                binding.root.setOnClickListener {
                    onArticleClick(this.slug)
                }
            }
        }
    }

    override fun getItemCount() = list.size
}