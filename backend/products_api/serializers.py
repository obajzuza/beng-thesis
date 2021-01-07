from rest_framework import serializers
from .models import Shelf, Product, ProductsOnShelves


class ShelfSerializer(serializers.ModelSerializer):
    class Meta:
        model = Shelf
        fields = '__all__'


class ProductsOnShelvesSerializer(serializers.ModelSerializer):
    class Meta:
        model = ProductsOnShelves
        fields = '__all__'


# class EachProductOnShelfSerializer(serializers.ModelSerializer):
#     amount = serializers.IntegerField(source='ProductsOnShelves.amount')
#
#     class Meta:
#         model = ProductsOnShelves
#         fields = ('amount',)


class ProductSerializer(serializers.ModelSerializer):
    # shelf = EachProductOnShelfSerializer(many=True, read_only=True)

    class Meta:
        model = Product
        # fields = '__all__'
        fields = ('id', 'code', 'product_name', 'manufacturer', 'shelves')
