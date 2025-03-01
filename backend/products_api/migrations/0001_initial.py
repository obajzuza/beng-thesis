# Generated by Django 3.1.4 on 2020-12-02 17:53

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Product',
            fields=[
                ('id', models.IntegerField(primary_key=True, serialize=False, unique=True)),
                ('product_name', models.CharField(max_length=100)),
                ('producent', models.CharField(max_length=100)),
            ],
        ),
        migrations.CreateModel(
            name='Shelf',
            fields=[
                ('id', models.IntegerField(primary_key=True, serialize=False, unique=True)),
            ],
        ),
        migrations.CreateModel(
            name='ProductsOnShelves',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('product', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='products_api.product')),
                ('shelf', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='products_api.shelf')),
            ],
        ),
        migrations.AddField(
            model_name='product',
            name='shelves',
            field=models.ManyToManyField(through='products_api.ProductsOnShelves', to='products_api.Shelf'),
        ),
    ]
