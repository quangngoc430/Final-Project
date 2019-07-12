from ShopTech import ShopTech
from surprise import SVD
import threading
import time
import json
import schedule 
from flask import Flask, Request, request, Response 

app = Flask(__name__)
ml = ShopTech()
algo = SVD()

def build_anti_test_set_for_user(test_subject, trainset):

    fill = trainset.global_mean

    anti_testset = []
    
    u = trainset.to_inner_uid(str(test_subject))
    
    user_items = set([j for (j, _) in trainset.ur[u]])
    anti_testset += [(trainset.to_raw_uid(u), trainset.to_raw_iid(i), fill) for
                             i in trainset.all_items() if
                             i not in user_items]
    return anti_testset

def build_recommendation_model():
    print("Loading item ratings...")
    data = ml.loadMovieLensLatestSmall()

    print("\nBuilding recommendation model...")
    trainSet = data.build_full_trainset()

    algo.fit(trainSet)

    return trainSet
    print('\nDone building recommendation model...')

def predict(test_subject, size):

    ShopTech.trainSet = build_recommendation_model()

    print("Computing recommendations...")
    trainSet = ShopTech.trainSet
    testSet = build_anti_test_set_for_user(test_subject, trainSet)
    predictions = algo.test(testSet)

    recommendations = []

    print ("\nWe recommend:")
    for user_id, movie_id, actual_rating, estimated_rating, _ in predictions:
        int_movie_id = int(movie_id)
        recommendations.append((int_movie_id, estimated_rating))

    recommendations.sort(key=lambda x: x[1], reverse=True)

    results = []

    for recommendation in recommendations[:int(size)]:
        results.append(recommendation[0])

    return results

#/user/<id>?size=<size>
@app.route('/user/<id>')
def recommendation_items_for_user(id):
    return Response(json.dumps(predict(id, request.args.get('size'))),  mimetype='application/json')

if __name__ == '__main__':
    app.run(debug=True, port=5000) #run app in debug mode on port 5000