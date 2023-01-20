package com.rahulghag.conduit.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rahulghag.conduit.databinding.ItemArticleBinding
import com.rahulghag.conduit.domain.models.Article

class ArticleAdapter(
    private val onArticleClick: (String) -> Unit
) : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallback()) {

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onArticleClick.invoke(getItem(position).slug)
                    }
                }
            }
        }

        fun bind(article: Article) {
            article.apply {
                binding.textViewUserAvatar.text = author.username.take(1).uppercase()

                binding.textViewAuthorName.text = author.username

                binding.textViewPublishedDate.text = formattedDate

                binding.textViewArticleTitle.text = title

                binding.textViewArticleDescription.text = description
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.slug == newItem.slug

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}