package com.sparta.spartanewsfeed.domain.article.service;

import org.springframework.stereotype.Service;

import com.sparta.spartanewsfeed.domain.article.entity.Article;
import com.sparta.spartanewsfeed.domain.article.entity.ArticleLike;
import com.sparta.spartanewsfeed.domain.article.repository.ArticleLikeRepository;
import com.sparta.spartanewsfeed.domain.article.repository.ArticleRepository;
import com.sparta.spartanewsfeed.domain.member.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

	private final ArticleRepository articleRepository;
	private final ArticleLikeRepository articleLikeRepository;

	public void toggleLike(Long articleId, Member member) {
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 입니다."));

		articleLikeRepository.findArticleLikeByArticleAndMember(article, member)
			.ifPresentOrElse(
				articleLikeRepository::delete,
				() -> {
					ArticleLike articleLike = ArticleLike.builder()
						.article(article)
						.member(member)
						.build();
					articleLikeRepository.save(articleLike);
				});
	}
}