package com.example.Recommendation.System;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

	public List<String> getRecommendations(long userId, int howMany) throws Exception {
		// Load the Reviews.csv file from the resources folder
		URL resource = getClass().getClassLoader().getResource("Reviews.csv");
		if (resource == null) {
			throw new IllegalArgumentException("Reviews.csv not found in resources folder!");
		}

		File file = new File(resource.toURI());
		DataModel model = new FileDataModel(file); // Assumes format: userId,itemId,score

		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
		Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		List<RecommendedItem> recommendations = recommender.recommend(userId, howMany);
		List<String> result = new ArrayList<>();

		for (RecommendedItem recommendation : recommendations) {
			result.add("Item: " + recommendation.getItemID() + " | Score: " + recommendation.getValue());
		}

		return result;
	}
}
