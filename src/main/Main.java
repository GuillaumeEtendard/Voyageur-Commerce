package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import model.City;
import model.Solution;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

class Main {

	public static void main(String[] args) throws FileNotFoundException{


		List<City> citiesList = dataCities();

		List<Solution> solutionsList = generateSolution(citiesList, 10);


		// Affichage de la solution initiale
		System.out.println("Liste initiale de solutions " + solutionsList);

		for(int i = 0; i < 10; i++){

			int solutionsSize2  = solutionsList.size() / 2;
			int solutionsSize = solutionsList.size();

			// Génération partielle d'une nouvelle solution qui remplace l'ancienne partie basse du tableau (sélection)
			List<Solution> solutionsList2 = generateSolution(citiesList, solutionsSize2);
			solutionsList.addAll(solutionsList2);
			

			// Set du score
			for(Solution solution : solutionsList){
				solution.setScore(getScore(solution.getCities()));
			}


			// Tri des solutions par score
			solutionsList = sortByScore(solutionsList);

			//Mutation :
			solutionsList = swapElements(solutionsList);
						
			// Suppression de la 2e partie basse du tableau (Sélection)
			for(int b = solutionsSize - 1; b >= solutionsSize2; b--){
				solutionsList.remove(solutionsList.get(b));
			}


		}

		// Affichage de la liste finale
		System.out.println("Liste finale de solutions " +solutionsList);
		// Affichage de la meilleure solution et de son score
		Solution bestSolution = bestSolution(solutionsList);
		System.out.println("Meilleure solution " + bestSolution);
		System.out.println(bestSolution.getScore());

	}

	private static Solution bestSolution(List<Solution> solutionsList){
		List<Solution> solutionsListBest = new ArrayList<Solution>(solutionsList);
		Double bestScore = 0.0;
		Solution bestSolution = null;
		int index = 0;
		for(Solution solution : solutionsListBest){
			if(index == 0){
				bestScore = solutionsListBest.get(index).getScore();
				bestSolution = solutionsListBest.get(index);
			}
			if(solutionsListBest.get(index).getScore() < bestScore){
				bestScore = solutionsListBest.get(index).getScore();
				bestSolution = solutionsListBest.get(index);
			}
			index++;
		}
		return bestSolution;
	}

	private static Double getScore(List<City> citiesList){
		double sum = 0;
		for(int i = 0; i < citiesList.size() -1; i++){

			float distance = citiesList.get(i).getDistanceBetween2Cities(citiesList.get(i+1));

			sum += distance;
		}

		return sum;
	}

	private static List<City> dataCities() throws FileNotFoundException{
		Reader reader = new FileReader(new File("./json/city_sample.json"));

		Gson gson = new GsonBuilder().create();
		return (List<City>) gson.fromJson(reader, new TypeToken<List<City>>() {}.getType());
	}

	private static List<City> populate(List<City> Cities) throws FileNotFoundException{
		List<City> tempsCities = new ArrayList<>(Cities);

		Collections.shuffle(tempsCities);

		return tempsCities;
	}

	private static List<Solution> generateSolution(List<City> Cities, int nombre) throws FileNotFoundException{
		List<Solution> solutionsList = new ArrayList<Solution>();
		List<City> citySolution = new ArrayList<City>(Cities);
		for(int i = 0; i < nombre; i++){
			List<City> population = populate(citySolution);
			Solution solution = new Solution(population, null);
			solutionsList.add(solution);
		}
		return solutionsList;
	}
	

	private static List<Solution> sortByScore(List<Solution> solutions){
		List<Solution> solutionsSort = new ArrayList<Solution>(solutions);
		
		Collections.sort(solutionsSort, new Comparator<Solution>() {

			@Override
			public int compare(Solution o1, Solution o2) {
				// TODO Auto-generated method stub
				return o1.getScore() < o2.getScore() ? -1 :o1.getScore()  == o2.getScore() ? 0 : 1;
			}
		});

		return solutionsSort;

	}
	
	
	private static List<Solution> swapElements(List<Solution> solutions){
		List<Solution> solutionsList = new ArrayList<Solution>(solutions);
		
		Random rand = new Random();
		int Low = solutionsList.size() / 2;
		int High = solutionsList.size();
		int rand1 = rand.nextInt(High-Low) + Low;
		int rand2 = rand.nextInt(High-Low) + Low;

		Collections.swap(solutionsList,rand1, rand2);

		return solutionsList;
	}
}
