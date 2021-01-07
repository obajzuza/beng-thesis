from django.db import models

# Create your models here.


class Shelf(models.Model):
    # class modelling a database table containing information about the shelf
    id = models.IntegerField(primary_key=True, unique=True)
    x = models.FloatField(null=True)
    y = models.FloatField(null=True)

    @classmethod
    def create(cls, id):
        return cls(id=id)


class Product(models.Model):
    # class modelling a database table containing information about the product
    # id = models.IntegerField(primary_key=True, unique=True)
    code = models.CharField(max_length=13)
    product_name = models.CharField(max_length=100)
    manufacturer = models.CharField(max_length=100)
    shelves = models.ManyToManyField(Shelf, through='ProductsOnShelves')


class ProductsOnShelves(models.Model):
    # class modelling a database table containing information about the products being stored on certain shelves and the amount of them in the location
    shelf = models.ForeignKey(Shelf, on_delete=models.CASCADE)
    product = models.ForeignKey(Product, on_delete=models.CASCADE)
    amount = models.IntegerField(default=0)

    @classmethod
    def create(cls, product_id, shelf, amount):
        shelf_instance = Shelf.create(shelf)
        return cls(product_id=product_id, shelf=shelf_instance, amount=amount)
